import boto3
import uuid
import hashlib
import re
import json

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('glimpse-user-table')
preferences_table = dynamodb.Table('glimpse-user-preferences')

def create_user(event, context):
    
    print_statement = f"Request received: {event}"
    print(print_statement)
    
    user_email = event['user_email']
    print(user_email)
    username = event['username']
    print(username)
    password = event['password']
    print(password)

    # Check if user_email already exists in the table
    response = table.scan(FilterExpression = 'user_email = :user_email',
        ExpressionAttributeValues = {':user_email': user_email})
    
    print("line 31")
    
    if response['Count'] > 0:
        return {"statusCode": 400, "body": "Email already exists."}
        
    print("line 36")
    
    # Check if username already exists in the table
    response = table.scan(FilterExpression = 'username = :username',
        ExpressionAttributeValues = {':username': username})
    if response['Count'] > 0:
        return {"statusCode": 400, "body": "Username already exists."}
        
    print("line 44")
    
    # Check if the username is between 5 and 12 characters long and contains only alphanumeric characters
    if not re.match('^[a-zA-Z0-9_]{5,12}$', username):
        return {"statusCode": 400, "body": "Invalid username."}
        
    print("line 50")
    
    # Check if the password is between 6 and 16 characters long and contains at least one capital letter, one number, and one special character
    if not re.match('^(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{6,16}$', password):
        return {"statusCode": 400, "body": "Invalid password."}
        
    print("line 56")
    
    # Hash the password and store only the hash value in the table
    password_hash = hashlib.sha256(password.encode('utf-8')).hexdigest()
    
    print("line 61")

    # Generate a new UUID for the user
    new_uuid = str(uuid.uuid4())
    
    print("line 66")

    # Create a new user item in the table
    user_item = {'UUID': new_uuid, 'user_email': user_email, 'username': username, 'password_hash': password_hash}
    table.put_item(Item=user_item)
    
    print("line 72")
    
    # Add the new user's UUID to the preferences table
    preferences_item = {'UUID': new_uuid}
    preferences_table.put_item(Item=preferences_item)
    
    print("line 78")

    # Create a new table for the user
    history_table_name = new_uuid + '-history'
    history_table = dynamodb.create_table(
        TableName=history_table_name,
        KeySchema=[
            {
                'AttributeName': 'number',
                'KeyType': 'HASH'
            }
        ],
        AttributeDefinitions=[
            {
                'AttributeName': 'number',
                'AttributeType': 'N'
            }
        ],
        ProvisionedThroughput={
            'ReadCapacityUnits': 5,
            'WriteCapacityUnits': 5
        }
    )

    # # Wait for the table to be created
    # history_table.meta.client.get_waiter('table_exists').wait(TableName=history_table_name)

    print(new_uuid)
    print(history_table_name)
    # Return the new user's UUID and the name of the new history table
    return {"statusCode": 200, "body": {"UUID": new_uuid, "history_table_name": history_table_name}}

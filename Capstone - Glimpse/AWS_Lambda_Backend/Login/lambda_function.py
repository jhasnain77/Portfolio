import boto3
import hashlib

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('glimpse-user-table')

def login(event, context):
    
    print(event)
    
    username_or_email = event['username_or_email']
    password = event['password']
    
    print(username_or_email)
    print(password)

    # Check if the user exists in the table with the given username or email
    response = table.scan(FilterExpression = 'username = :username or user_email = :user_email',
        ExpressionAttributeValues = {':username': username_or_email, ':user_email': username_or_email})
    if response['Count'] == 0:
        return {"statusCode": 401, "body": "Incorrect username or password."}
    
    user = response['Items'][0]
    # Hash the provided password and check if it matches the password hash in the table
    password_hash = hashlib.sha256(password.encode('utf-8')).hexdigest()
    if user['password_hash'] != password_hash:
        return {"statusCode": 401, "body": "Incorrect username or password."}
    
    # Return success if the password is correct
    return {"statusCode": 200, "body": user['UUID']}

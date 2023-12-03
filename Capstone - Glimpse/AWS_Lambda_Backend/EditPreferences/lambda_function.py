import boto3
import json

dynamodb = boto3.resource('dynamodb')
preferences_table = dynamodb.Table('glimpse-user-preferences')

def edit_preferences(event, context):
    uuid = event['UUID']
    name = event.get('name', None)
    genres = event.get('genres', None)

    # Check if the preferences item exists
    response = preferences_table.get_item(Key={'UUID': uuid})
    if 'Item' not in response:
        return {"statusCode": 404, "body": "User preferences not found."}

    # Update the preferences item
    update_expression = "SET #n = :name, "
    expression_attribute_names = {"#n": "name"}
    expression_attribute_values = {":name": name}
    if genres is not None:
        update_expression += "genres = :genres"
        expression_attribute_values[":genres"] = genres
    else:
        update_expression = update_expression.rstrip(", ")
    preferences_table.update_item(
        Key={'UUID': uuid},
        UpdateExpression=update_expression,
        ExpressionAttributeNames=expression_attribute_names,
        ExpressionAttributeValues=expression_attribute_values
    )

    # Return the updated preferences item
    response = preferences_table.get_item(Key={'UUID': uuid})
    return {"statusCode": 200, "body": response['Item']}

import boto3
import json
from decimal import Decimal

# Initialize the DynamoDB client
dynamodb = boto3.client('dynamodb')

def get_history_items(event, context):
    
    print(event)
    
    # get the UUID from the request
    uuid = event['uuid']
    
    # append "-history" to the UUID to get the table name
    table_name = f"{uuid}-history"
        
    # retrieve data from the table
    response = dynamodb.scan(TableName=table_name)
    data = response['Items']
        
    # convert Decimal values to float format
    for item in data:
        for key in item:
            if isinstance(item[key], Decimal):
                item[key] = float(item[key])
                
    # Concatenate the image segments if there are multiple segments
    for item in data:
        image_segments = []
        for key, value in item.items():
            if key == 'image':
                if isinstance(value, dict):
                    print(f"value: {value}")
                    if 'S' in value:
                        item['image'] = value['S']
                    else:
                        print(f"'S' key not found in value dictionary: {value}")
                else:
                    print(f"value is not a dictionary: {value}")
            elif key.startswith('image_'):
                image_segments.append(value['S'])
    
    # build the response body
    items = {}
    for i, item in enumerate(data):
        items[f'item{i+1}'] = item
    
    # return the retrieved data as a JSON object
    return {
        'statusCode': 200,
        'body': json.dumps(items)
    }

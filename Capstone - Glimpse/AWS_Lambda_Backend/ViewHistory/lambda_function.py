import json
import boto3

dynamodb = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    uuid = event['uuid']
    
    # append "-history" to the UUID to get the table name
    table_name = "{uuid}-history"
   
    # retrieve data from the table
    response = dynamodb.scan(
        TableName=table_name
    )
    data = response['Items']

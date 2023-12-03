export type AmplifyDependentResourcesAttributes = {
    "auth": {
        "Glimpse2": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string"
        }
    },
    "storage": {
        "s3glimpse2storage1aacef11": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "function": {
        "lambdafunctiontest": {
            "Name": "string",
            "Arn": "string",
            "Region": "string",
            "LambdaExecutionRole": "string"
        }
    },
    "api": {
        "glimpse2": {
            "GraphQLAPIKeyOutput": "string",
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    }
}

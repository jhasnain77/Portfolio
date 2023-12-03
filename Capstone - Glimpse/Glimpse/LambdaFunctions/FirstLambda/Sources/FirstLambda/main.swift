import AWSLambdaEvents
import AWSLambdaRuntime
import Foundation

// Response, uses Codable for transparent JSON encoding
private struct Response: Codable {
  let message: String
}

// In this example we are receiving and responding with `Codable`.
Lambda.run { (context, request: Words, callback: @escaping (Result<Response, Error>) -> Void) in
  callback(.success(Response(message: "The One That You Love")))
}

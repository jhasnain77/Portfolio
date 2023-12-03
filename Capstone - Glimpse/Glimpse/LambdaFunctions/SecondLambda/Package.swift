// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "SecondLambda",
    platforms: [.macOS(.v10_13)],
    products: [
        .executable(
            name: "SecondLambda",
            targets: ["SecondLambda"]),
    ],
    dependencies: [
        .package(url: "https://github.com/swift-server/swift-aws-lambda-runtime.git", .upToNextMajor(from: "0.3.0"))
    ],
    targets: [
        .target(
            name: "SecondLambda",
            dependencies: [
                .product(name: "AWSLambdaRuntime", package: "swift-aws-lambda-runtime"),
                .product(name: "AWSLambdaEvents", package: "swift-aws-lambda-runtime")
            ]),
        .testTarget(
            name: "SecondLambdaTests",
            dependencies: ["SecondLambda"]),
    ]
)
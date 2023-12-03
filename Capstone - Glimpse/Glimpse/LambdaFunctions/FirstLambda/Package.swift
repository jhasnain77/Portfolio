// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "FirstLambda",
    platforms: [.macOS(.v10_13)],
    products: [
        .executable(
            name: "FirstLambda",
            targets: ["FirstLambda"]),
    ],
    dependencies: [
        .package(url: "https://github.com/swift-server/swift-aws-lambda-runtime.git", .upToNextMajor(from: "0.3.0"))
    ],
    targets: [
        .target(
            name: "FirstLambda",
            dependencies: [
                .product(name: "AWSLambdaRuntime", package: "swift-aws-lambda-runtime"),
                .product(name: "AWSLambdaEvents", package: "swift-aws-lambda-runtime")
            ]),
        .testTarget(
            name: "FirstLambdaTests",
            dependencies: ["FirstLambda"]),
    ]
)
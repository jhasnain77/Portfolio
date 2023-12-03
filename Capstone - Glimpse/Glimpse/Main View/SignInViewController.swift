//
//  SignInViewController.swift
//  Glimpse
//
//  Created by Aly Lin on 2023-03-20.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit

class SignInViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
        self.hideKeyboardWhenTappedAround()

       
 
    }
    
    let userDefaultsSignIn = UserDefaults.standard
    var ableToLogIn = false
    
    @IBOutlet weak var usernameOrEmailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var signInButton: UIButton!
    @IBOutlet weak var errorMessage: UILabel!
    
    
    
//    @IBOutlet weak var comingSoonView: UIView!
//    @IBAction func functionComingSoon(_ sender: Any) {
//        self.comingSoonView.isHidden = false
//    }
//
//    self.comingSoonView.isHidden = true
//
//    @IBAction func continueButton() {
//        comingSoonView.isHidden = true
//
//    }
    
    @IBAction func signInButtonPressed() {
        sendLoginInfoToLambda()

    }
    
    @IBAction func backButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "signInToSettingsScreen", sender: self)
    }
}

extension SignInViewController {
    
    private func sendLoginInfoToLambda() {
        
        var username: String = usernameOrEmailTextField.text!
        
        userDefaultsSignIn.set(username, forKey: "username")
        userDefaultsSignIn.set(true, forKey: "loggedIn")

        
        var usernameOREmail: String = usernameOrEmailTextField.text!
        var password: String = passwordTextField.text!
        
        let path = "https://rhw7t1rzz0.execute-api.us-east-2.amazonaws.com/default/Login"
        
        var request = URLRequest(url: URL(string: path)!)
        
        let logininfo = ["username_or_email": usernameOREmail, "password": password]
        
        guard let jsonData = try? JSONSerialization.data(withJSONObject: logininfo, options: []) else {
            print("Error: Unable to serialize JSON")
            return
        }
        
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = jsonData
        
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            
            if let error = error {
                print("Error: \(error.localizedDescription)")
                return
            }
            
            // Check if there is data in the response
            guard let data = data else {
                print("Error: No data in response")
                return
            }
            
            // Decode JSON data
            guard let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String: Any] else {
                print("Error: Unable to decode JSON data")
                return
            }
            
            print("the response from the lambda function is:")
            // Print JSON in console
            print(json)
            self.parseLambdaResponse(json)
            
        }
        task.resume()
        
    }
    
    private func parseLambdaResponse(_ json: [String: Any]) {
        
        // Get body from JSON
        guard let statusCode = json["statusCode"] as? Int else {
            print("Error: Unable to get statusCode from JSON")
            return
        }
            
        guard let body = json["body"] as? String else {
            print("Error: Unable to get invalidMessage_fromJson from JSON")
                return
        }
        
        if (body != "Incorrect username or password.") {
            
            userDefaultsSignIn.set(body, forKey: "uuid")
            
            if let value = userDefaultsSignIn.object(forKey: "uuid") as? String {
                print("userDefaults: " + value)
            }
            
        }
        print(body)
        DispatchQueue.main.async {
                if (body != "Incorrect username or password.") {
                    self.performSegue(withIdentifier: "signInToMainScreen", sender: self)
                } else {
                    self.errorMessage.text = body
                        }
            }
        
    }
    
}

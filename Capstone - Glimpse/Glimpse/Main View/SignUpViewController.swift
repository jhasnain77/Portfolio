//
//  SignUpViewController.swift
//  Glimpse
//
//  Created by Aly Lin on 2023-03-20.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit

class SignUpViewController: UIViewController {
    
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var createAccountButton: UIButton!
    @IBOutlet weak var errorMessage: UILabel!
    @IBOutlet weak var requirementMessage: UILabel!
    @IBOutlet weak var comingSoonView: UIView!
    
    @IBAction func createAccountButtonPressed() {
        sendAccountInfoToLambda()
        print("createAccountButtonPressed")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.hidesBackButton = true
        NavigationStack.shared.push(self)
        self.hideKeyboardWhenTappedAround()

       
 
    }
    
}
extension SignUpViewController {
    
    private func sendAccountInfoToLambda() {
        var username: String = usernameTextField.text!
        var email: String = emailTextField.text!
        var password: String = passwordTextField.text!
        
        
        let path = "https://hjho86940i.execute-api.us-east-2.amazonaws.com/default/CreateAccount"
        
        var request = URLRequest(url: URL(string: path)!)
        
        let accountinfo = ["user_email": email, "username": username, "password": password]
        
        guard let jsonData = try? JSONSerialization.data(withJSONObject: accountinfo, options: []) else {
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
        
        var uuid: String = ""
        var history_table_name: String = ""
        var invalidMessage: String = ""
        
        // Get body from JSON
        guard let statusCode = json["statusCode"] as? Int else {
            print("Error: Unable to get statusCode from JSON")
            return
        }
        
        if statusCode == 200 {
            
            // Get body from JSON
            guard let body = json["body"] as? [String: Any] else {
                print("Error: Unable to get body from JSON")
                return
            }
            
            guard let history_table_name_fromJson = body["history_table_name"] as? String else {
                print("Error: Unable to get history_table_name from JSON")
                return
            }
            
            guard let uuid_fromJson = body["UUID"] as? String else {
                print("Error: Unable to get UUID from JSON")
                return
            }
            
            history_table_name = history_table_name_fromJson
            uuid = uuid_fromJson
            
            print(uuid)
            print(history_table_name)
            //go to login
            DispatchQueue.main.async {
                self.performSegue(withIdentifier: "signUpToSignInScreen", sender: self)
            }
        }
        
        else if statusCode == 400 {
            
            guard let invalidMessage_fromJson = json["body"] as? String else {
                print("Error: Unable to get invalidMessage_fromJson from JSON")
                return
            }
            
            invalidMessage = invalidMessage_fromJson
            print(invalidMessage)
            //display on ui
            DispatchQueue.main.async {
                self.errorMessage.text = invalidMessage
                if(invalidMessage == "Invalid username.")
                {
                    self.requirementMessage.text = "Username must be between 5-12 characters, and be alphanumeric (no special characters)"
                }
                else if(invalidMessage == "Invalid password.")
                {
                    self.requirementMessage.text = "Password must be between 6-16 characters and contain at least: 1 capital letter, 1 number, 1 special character"
                }
            }
        }
    }
    
    
    @IBAction func backButtonClicked(_ sender: Any) {
        self.performSegue(withIdentifier: "signUpToSettingsScreen", sender: self)
    }
    

}

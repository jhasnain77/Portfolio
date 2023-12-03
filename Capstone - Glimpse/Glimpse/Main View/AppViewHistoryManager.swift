//
//  AppViewHistoryManager.swift
//  Glimpse
//
//  Created by Isabel Higgon on 2023-03-21.
//  Copyright © 2023 Apple. All rights reserved.
//

import Foundation
import UIKit


class NavigationStack {
    static let shared = NavigationStack()
    
    private var stack = [UIViewController]()
    
    func push(_ viewController: UIViewController) {
        stack.append(viewController)
    }
    
    func pop() -> UIViewController? {
        return stack.popLast()
    }
    
    var lastVisitedViewController: UIViewController? {
        return stack.last
    }
}

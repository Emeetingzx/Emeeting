//
//  EMMNetworkActivityIndicatorManager.h
//  EMMNetWork
//
//  Created by clement on 14-4-24.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface EMMNetworkActivityIndicatorManager : NSObject

/**
*   标记是否在管理网络
*/
@property (nonatomic, assign, getter = isEnabled) BOOL enabled;

/**
 *  标记是否网络Indicator可视
 */
@property (readonly, nonatomic, assign) BOOL isNetworkActivityIndicatorVisible;

/**
 *  单例方法
 *
 *  @return EMMNetworkActivityIndicatorManager 对象
 */
+ (instancetype)sharedManager;

/**
 *  递增网络状态
 */
- (void)incrementActivityCount;

/**
 *  递减网络状态
 */
- (void)decrementActivityCount;

@end

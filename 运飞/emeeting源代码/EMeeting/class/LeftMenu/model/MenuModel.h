//
//  MenuModel.h
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface MenuModel : NSObject

@property (nonatomic, copy) NSString *imageName;
@property (nonatomic, copy) NSString *textName;
@property (nonatomic, strong) UIColor *color;

- (instancetype)initWithImageName:(NSString *)imageName andTextName:(NSString *)textName andColor:(UIColor *)color;

@end

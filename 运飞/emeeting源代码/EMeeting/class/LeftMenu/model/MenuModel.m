//
//  MenuModel.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MenuModel.h"

@implementation MenuModel

-(instancetype)initWithImageName:(NSString *)imageName andTextName:(NSString *)textName andColor:(UIColor *)color
{
    if (self = [super init])
    {
        _imageName = imageName;
        _textName = textName;
        _color = color;
    }
    return self;
}

@end

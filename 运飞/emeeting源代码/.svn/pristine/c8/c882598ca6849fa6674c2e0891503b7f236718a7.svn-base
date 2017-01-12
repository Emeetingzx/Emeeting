//
//  UINavigationController+Custom.m
//  RedPacket
//
//  Created by itp on 15/12/10.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "UINavigationController+Custom.h"

@implementation UINavigationController (Custom)

- (void)changeStackWithSencodViewController:(UIViewController *)viewcontroller
{
    if (viewcontroller == nil) {
        return;
    }
    NSArray *stacks = self.viewControllers;
    if (stacks.count > 0)
    {
        NSMutableArray *array = [NSMutableArray array];
        [array addObject:stacks[0]];
        [array addObject:viewcontroller];
        self.viewControllers = array;
    }
}

@end

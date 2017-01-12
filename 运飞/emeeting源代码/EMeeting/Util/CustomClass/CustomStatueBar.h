//
//  CustomStatueBar.h
//  CustomStatueBar
//
//  Created by efutureinfo on 16/5/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
//#import "BBCyclingLabel.h"

@interface CustomStatueBar : UIWindow
{
//    BBCyclingLabel *defaultLabel;
    BOOL isFull;
    UILabel *defaultLabel;
}
- (void)showStatusMessage:(NSString *)message;
- (void)hide;
- (void)changeMessge:(NSString *)message;
@end

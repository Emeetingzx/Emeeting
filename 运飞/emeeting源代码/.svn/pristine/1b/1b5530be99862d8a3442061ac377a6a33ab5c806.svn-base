//
//  ProgressView.m
//  RedPacket
//
//  Created by efutureinfo.cn on 15/12/17.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "ProgressView.h"
#import "MBProgressHUD.h"
@implementation ProgressView

+(void)showHUDAddedTo:(UIView *) view ProgressText:(NSString *)progressTex
{
    if (view) {
        MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:view animated:YES];
        resultHUD.labelText = progressTex;
        [resultHUD hide:YES afterDelay:1.5];

    }
}

+(void)showTextHUDAddedTo:(UIView *) view ProgressText:(NSString *)progressTex
{
    if (view) {
        MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:view animated:YES];
        resultHUD.mode = MBProgressHUDModeText;
        resultHUD.labelText = progressTex;
        [resultHUD hide:YES afterDelay:1.5];
    }
}
+(void)showHUDAddedToProgress:(UIView *) view ProgressText:(NSString *)progressTex
{
    if (view) {
        MBProgressHUD *resultHUD = [MBProgressHUD showHUDAddedTo:view animated:YES];
        resultHUD.labelText = progressTex;
    }
}

+ (void)showCustomProgressViewAddTo:(UIView *)view
{
    if (view)
    {
        [MBProgressHUD showHUDAddedTo:view animated:YES];
    }
}

+ (void)hiddenCustomProgressViewAddTo:(UIView *)view
{
    if (view)
    {
        //[MBProgressHUD hideHUDForView:view animated:NO];
        [MBProgressHUD hideAllHUDsForView:view animated:YES];
    }
}



@end

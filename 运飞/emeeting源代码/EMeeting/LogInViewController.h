//
//  LogInViewController.h
//  RedPacket
//
//  Created by itp on 15/10/27.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol LogInViewControllerDegeate <NSObject>

-(void)homeVC;

@end

@interface LogInViewController : UIViewController
@property(assign)id <LogInViewControllerDegeate>deleagte;
@end

//
//  TouchRefreshView.h
//  EMeeting
//
//  Created by efutureinfo on 16/3/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TouchRefreshView;

@protocol TouchRefreshViewDelegate <NSObject>

@optional
- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView;

@end

@interface TouchRefreshView : UIView

@property (nonatomic, copy) NSString *title;

@property (nonatomic, weak) id<TouchRefreshViewDelegate> delegate;

@end

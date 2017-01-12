//
//  AlertTableView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

#define CancelIndex 10000

@class AlertTableView;

@protocol AlertTableViewDelegate <NSObject>

@optional
- (void)AlertTableView:(AlertTableView *)alertTable andDidClickButtonIndex:(NSInteger)index andClickButtonIndexText:(NSString *)text;

@end

@interface AlertTableView : UIView

-(instancetype)initWithFrame:(CGRect)frame andSelectedButtonString:(NSString *)selectedStr andOtherButtons:(NSArray<NSString *> *)dataArr andDelegate:(id)delegate;

@property (nonatomic, strong) NSArray *dataArr;

@property (nonatomic, copy) NSString *selectedButtonStr;

@property (nonatomic, weak) id<AlertTableViewDelegate> delegate;

@end

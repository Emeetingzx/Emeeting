//
//  AllMeetingCollectionViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AllMeetingCollectionViewCell.h"

@implementation AllMeetingCollectionViewCell

- (nonnull instancetype)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (!self) return nil;
    return self;
}
- (void)awakeFromNib {
    // Initialization code
}
- (void)setModel:(RMCalendarModel *)model {
    _model = model;
    
    
    switch (model.style) {
        case CellDayTypeEmpty:
            //self.bgImageView.hidden=YES;
            self.day.hidden=YES;
            self.bgImageView.image=nil;
            break;
//        case CellDayTypeMeeting:
//            self.bgImageView.image=[UIImage imageNamed:@"allmeeting_circular1"];
//            self.day.hidden=NO;
//             self.day.text=[NSString stringWithFormat:@"%lu",(unsigned long)model.day];
//            break;
//            
        case CellDayTypeClick:
            
            self.bgImageView.image=[UIImage imageNamed:@"allmeeting_circular2"];
            self.day.hidden=NO;
            self.day.text=[NSString stringWithFormat:@"%lu",(unsigned long)model.day];
            break;
        case CellDayTypeFutur:
            self.bgImageView.image=nil;
            self.day.text=[NSString stringWithFormat:@"%lu",(unsigned long)model.day];
            self.day.hidden=NO;
            if (model.isHaveMeeting) {
                self.bgImageView.image=[UIImage imageNamed:@"allmeeting_circular1"];
            }

            break;
        default:
            break;
    }

}
@end

//
//  PlaceTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "PlaceTableViewCell.h"

@implementation PlaceTableViewCell

- (void)awakeFromNib {
    // Initialization code
    self.rescindBtn.layer.cornerRadius=4;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end

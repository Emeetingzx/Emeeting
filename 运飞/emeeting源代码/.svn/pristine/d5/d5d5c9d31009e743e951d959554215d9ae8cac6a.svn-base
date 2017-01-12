//
//  UserImageCacheManager.h
//  RedPacket
//
//  Created by itp on 15/12/18.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UserImageCacheManager : NSObject

+ (void)loadHeaderImageWithUID:(NSString *)uid placeholder:(NSString *)placeholder imageView:(UIImageView *)imageView;
+ (void)loadHeaderImageWithUrl:(NSString *)imageurl placeholder:(NSString *)placeholder imageView:(UIImageView *)imageView;
+ (void)loadGiftImageWithGiftUrl:(NSString *)giftUrl placeholder:(NSString *)placeholder imageView:(UIImageView *)imageView completionBlock:(void(^)(BOOL success))completionBlock;

@end

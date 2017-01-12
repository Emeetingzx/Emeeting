//
//  UIImageView+EMMFileCache.h
//  EMMFileCache
//
//  Created by itp on 15/11/20.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EMMFileManager.h"

@interface UIImageView (EMMFileCache)

+ (void)emm_setFileManager:(EMMFileManager *)fileManager;


- (void)emm_setImageWithURL:(NSURL *)url;


- (void)emm_setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder;


- (void)emm_setImageWithURL:(NSURL *)url completed:(EMMFileCompletionWithFinishedBlock)completedBlock;


- (void)emm_setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder completed:(EMMFileCompletionWithFinishedBlock)completedBlock;

- (void)emm_setImageWithURL:(NSURL *)url placeholderImage:(UIImage *)placeholder progress:(EMMFileDownloadProgressBlock)progressBlock completed:(EMMFileCompletionWithFinishedBlock)completedBlock;


- (void)emm_setImageWithPreviousCachedImageWithURL:(NSURL *)url andPlaceholderImage:(UIImage *)placeholder  progress:(EMMFileDownloadProgressBlock)progressBlock completed:(EMMFileCompletionWithFinishedBlock)completedBlock;

- (void)emm_cancelCurrentImageLoad;

@end

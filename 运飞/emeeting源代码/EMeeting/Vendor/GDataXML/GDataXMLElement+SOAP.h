//
//  GDataXMLElement+SOAP.h
//  EmployeeServiceCenter
//
//  Created by itp on 14-9-2.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import "GDataXMLNode.h"

@interface GDataXMLElement (SOAP)

- (GDataXMLElement *)resultElementWithresponseName:(NSString *)responseName resultName:(NSString *)resultName;

@end

//
//  GDataXMLElement+SOAP.m
//  EmployeeServiceCenter
//
//  Created by itp on 14-9-2.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import "GDataXMLElement+SOAP.h"

@implementation GDataXMLElement (SOAP)

- (GDataXMLElement *)resultElementWithresponseName:(NSString *)responseName resultName:(NSString *)resultName
{
    NSArray *array = [self nodesForXPath:@"/soapenv:Envelope/soapenv:Body" error:NULL];
    if ([array count] > 0) {
        GDataXMLElement *element = [array objectAtIndex:0];
        NSArray *elements = [element elementsForName:responseName];
        if ([elements count] > 0) {
            GDataXMLElement *responseElement = [elements objectAtIndex:0];
            NSArray *results =[responseElement elementsForName:resultName];
            GDataXMLElement *resultElement = [results objectAtIndex:0];
            return resultElement;
        }
    }
    return nil;
}

@end

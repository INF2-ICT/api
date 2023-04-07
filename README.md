# Quintor INF2-2 - Api

## Description

This API provides database utilities for the Quintor Application made by INF2-2 of NHLStenden.

## Endpoints

### Endpoint post-xml

- **Description**: Reads out an xml file with mt940 tags and pushes it to the database.
- **Endpoint**: `/post-xml`
- **HTTP Method**: post
- Request Parameters
  - xml: mt940 file parsed in xml
- Parameter content

```
<MT940>
   <line20>
      <reference>P140220000000001</reference>
   </line20>
   <line25>
      <account>NL69INGB0123456789EUR</account>
   </line25>
   <line28C>
      <statementNumber>00000</statementNumber>
      <sequenceNumber>null</sequenceNumber>
   </line28C>
   <line60F>
      <creditDebit>C</creditDebit>
      <date>140219</date>
      <currency>EUR</currency>
      <amount>662,23</amount>
   </line60F>
   <statements>
      <statement0>
         <line61>
            <valueDate>140220</valueDate>
            <entryDate>0220</entryDate>
            <creditDebit>C</creditDebit>
            <fundCode>null</fundCode>
            <amount>1,56</amount>
            <identifierCode>TRF</identifierCode>
            <customerReference>EREF</customerReference>
            <bankReference>00000000001005</bankReference>
            <supplementaryDetails>/TRCD/00100/</supplementaryDetails>
         </line61>
         <line86>
            <line1>/EREF/EV12341REP1231456T1234//CNTP/NL32INGB0000012345/INGBNL2</line1>
            <line2>A/ING BANK NV INZAKE WEB///REMI/USTD//EV10001REP1000000T1000/</line2>
            <line3>null</line3>
            <line4>null</line4>
            <line5>null</line5>
            <line6>null</line6>
         </line86>
      </statement0>
   </statements>
   <line62F>
      <creditDebit>C</creditDebit>
      <date>140220</date>
      <currency>EUR</currency>
      <amount>564,35</amount>
   </line62F>
</MT940>
```

**Response Codes**:

- 200 OK: "Success"
- 400 Bad Request: "XML is not valid"

### Endpoint post-json

- **Description**: Reads out an json file with mt940 tags and pushes it to the database.
- **Endpoint**: `/post-json`
- **HTTP Method**: post
- Request Parameters
  - json: mt940 file parsed in json
- Parameter content

```
{
    "MT940": {
        "line25": {
            "account": "NL69INGB0123456789EUR"
        },
        "line60F": {
            "date": "140219",
            "amount": "662,23",
            "currency": "EUR",
            "creditDebit": "C"
        },
        "line28C": {
            "sequenceNumber": null,
            "statementNumber": "00000"
        },
        "line20": {
            "reference": "P140220000000001"
        },
        "statements": {
            "statement0": {
                "line61": {
                    "amount": "1,56",
                    "bankReference": "00000000001005",
                    "entryDate": "0220",
                    "fundCode": null,
                    "identifierCode": "TRF",
                    "customerReference": "EREF",
                    "supplementaryDetails": "/TRCD/00100/",
                    "valueDate": "140220",
                    "creditDebit": "C"
                },
                "line86": {
                    "line6": null,
                    "line5": null,
                    "line4": null,
                    "line3": null,
                    "line2": "A/ING BANK NV INZAKE WEB///REMI/USTD//EV10001REP1000000T1000/",
                    "line1": "/EREF/EV12341REP1231456T1234//CNTP/NL32INGB0000012345/INGBNL2"
                }
            }
        },
        "line62F": {
            "date": "140220",
            "amount": "564,35",
            "currency": "EUR",
            "creditDebit": "C"
        }
    }
}
```

**Response Codes**:

- 200 OK: "Success"
- 400 Bad Request: "JSON is not valid"

### Endpoint post-raw

- **Description**: Reads out an raw mt940 file and pushes it to the NOSQL database.
- **Endpoint**: `/post-raw`
- **HTTP Method**: post
- Request Parameters
  - MT940File: mt940 file
- Parameter content

```
{1:F01INGBNL2ABXXX0000000000}
{2:I940INGBNL2AXXXN}
{4:
:20:P140220000000001
:25:NL69INGB0123456789EUR
:28C:00000
:60F:C140219EUR662,23
:61:1402200220C1,56NTRFEREF//00000000001005
/TRCD/00100/
:86:/EREF/EV12341REP1231456T1234//CNTP/NL32INGB0000012345/INGBNL2
A/ING BANK NV INZAKE WEB///REMI/USTD//EV10001REP1000000T1000/
:62F:C140220EUR564,35
:64:C140220EUR564,35
:65:C140221EUR564,35
:65:C140224EUR564,35
:86:/SUM/4/4/134,46/36,58/
-}
```

**Response Codes**:

- 200 OK: "Success"
- 400 Bad Request: "An error has occurred uploading the raw file"


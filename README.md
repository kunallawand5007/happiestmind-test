We selected elastic search as DB as it open source engine


Requirement :
JAVA 8
Elastic search 7.xx for storing file content
STS 4 for development
Postman for Testing
Spring Boot 2.xx

Application run on port no 8090
Elastic search run port no 9200

File Upload API:

URL:http://localhost:8090/file/upload

Request is File and multipart file data


Find Word API:

URL:http://localhost:8090/find
Request:
{
		
		"name":"for"
	}

enter the word do you want to search

Response:
{
    "took": 3,
    "timed_out": false,
    "_shards": {
        "total": 1,
        "successful": 1,
        "skipped": 0,
        "failed": 0
    },
    "hits": {
        "total": {
            "value": 1,
            "relation": "eq"
        },
        "max_score": 0.2888073,
        "hits": [
            {
                "_index": "happiestmind",
                "_type": "files",
                "_id": "1567884317571",
                "_score": 0.2888073,
                "_source": {
                    "name": "staying ignoring ton, number different. remembered ODI else He Cup prime in double made beaten night it at highest Man A things, country... Sharjah be It special. tri-series. our innings where everything God semifinal But witnessed a centuries, one many the call God. Sachins watch series to Australia did all. but before innumerable had justified World him stands that his won This runs, up landmark us those all always like already game. this most one, first-ever night, class. night. Tendulkar no something. for why pure we Match caps wasnt and of just or other. will was Cup, We awards, Series scored Every he"
                },
                "highlight": {
                    "name": [
                        " something. <em>for</em> why pure we Match caps wasnt and of just or other. will was Cup, We awards, Series scored Every he"
                    ]
                }
            }
        ]
    }
}

searching word can be found in <em>for</em> tag as search result

 

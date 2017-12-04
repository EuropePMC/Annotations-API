# Annotations-API
This project contains the source code used to build the [Europe PMC Annotations API RESTful service][5]

Europe PMC Annotations API provides text mining annotations contained in abstracts and open access full text articles, using the [W3C Open Annotation Data Model][1]

The code has been written using JAVA 8. Maven has been chosen as development tool. The RESTful service  has been built using [Spring Boot][2]. The artifact produced by the project is a war file ready to be deployed on a Tomcat 8 container. 
The Europe PMC Annotations API is reading the data from a MongoDB database. The dependency that it is managing the mongoDB interaction is

{% highlight xml %}
<dependency>
       <groupId>uk.ac.ebi.literature</groupId>
	  <artifactId>mongodb_layer</artifactId>
	  <version>3.2.0</version>
	  <exclusions>
		  <exclusion> 
		    <groupId>org.slf4j</groupId>
		    <artifactId>slf4j-log4j12</artifactId>
		  </exclusion>
		  <exclusion> 
		    <groupId>log4j</groupId>
		    <artifactId>log4j</artifactId>
		  </exclusion>
	</exclusions> 
    </dependency>
{% highlight xml %}

In order to get this library it is necessary to download the [MongoDB Layer][3] component. 
The mongoDB database from where to read the data is specified in the property mongoDBUrl of the relative property file inside the folder src/main/resources. You can determine how to choose the property file to load in the class uk.ac.ebi.scilite.controller.AnnotationsAPIWebApplication. This class is also the entry point of the entire application. The value of the property mongoDBUrl will need to reflect one of the values of the enum MONGODB_URL defined in the class uk.ac.ebi.literature.mongodb.dao.ICrudDAO of the MongoDB Layer component. To see how this value will be translated to a specific mongoDB server address, see details of the MongoDB Layer component ReadME file.
The Europe PMC Annotations API is expecting that the documents inside the mongoDB database collection annotationsApi will have the following structure

<pre>
{
    "_id" : ObjectId("59e0c43a828d3fdefcede06d"),
    "src" : "MED",
    "ext_id" : "17511879",
    "uniqueCountId" : NumberLong(717511879),
    "pmcid" : NumberLong(1906825),
    "license" : "cc by",
    "oa" : "O",
    "anns" : [ 
        {
            "ann" : "http://rdf.ebi.ac.uk/resource/europepmc/annotations/PMC1906825#141-2",
            "position" : "141.2",
            "tags" : [ 
                {
                    "name" : "HPRT",
                    "uri" : "http://purl.uniprot.org/uniprot/Q26997"
                }
            ],
            "prefix" : "1test gene/",
            "exact" : "HPRT",
            "postfix" : "-1 expression ratios as measu",
            "ann_link" : "http://europepmc.org/articles/PMC1906825#europepmc_141-2",
            "type" : "GENES_PROTEINS",
            "section" : "Results",
            "provider" : "europepmc"
        }, 
        {
            "ann" : "http://rdf.ebi.ac.uk/resource/europepmc/annotations/PMC1906825#142-1",
            "position" : "142.1",
            "tags" : [ 
                {
                    "name" : "Gene Expression",
                    "uri" : "http://identifiers.org/go/GO:0010467"
                }
            ],
            "prefix" : "",
            "exact" : "Gene Expression",
            "postfix" : " Levels in Normal Breast Tissue Compared",
            "ann_link" : "http://europepmc.org/articles/PMC1906825#europepmc_142-1",
            "type" : "GO_TERMS",
            "section" : "Results",
            "provider" : "europepmc"
        }, 
        {
            "ann" : "http://rdf.ebi.ac.uk/resource/europepmc/annotations/PMC1906825#142-3",
            "position" : "142.3",
            "tags" : [ 
                {
                    "name" : "Breast Tumors",
                    "uri" : "http://linkedlifedata.com/resource/umls-concept/C1458155"
                }
            ],
            "prefix" : "mal Breast Tissue Compared to Sporadic ",
            "exact" : "Breast Tumors",
            "postfix" : " ",
            "ann_link" : "http://europepmc.org/articles/PMC1906825#europepmc_142-3",
            "type" : "DISEASE",
            "section" : "Results",
            "provider" : "europepmc"
        }, 
        .......
    ],
    "kw" : [ 
        {
            "nm" : "ANN_PROVIDER",
            "val" : "europepmc",
            "count" : 281,
            "uniqueCount" : 281.717511879
        }, 
        {
            "nm" : "ANN_TYPE",
            "val" : "GENES_PROTEINS",
            "count" : 159,
            "uniqueCount" : 159.717511879
        }, 
        {
            "nm" : "ANN_ENTITY",
            "val" : "brca1",
            "count" : 116,
            "uniqueCount" : 116.717511879
        }, 
        {
            "nm" : "ANN_SECTION_TYPE",
            "val" : "genes_proteins%title",
            "count" : 1,
            "uniqueCount" : 1.717511879
        }, 
        {
            "nm" : "ANN_SECTION",
            "val" : "Title",
            "count" : 4,
            "uniqueCount" : 4.717511879
        }, 
        {
            "nm" : "ANN_TYPE",
            "val" : "DISEASE",
            "count" : 63,
            "uniqueCount" : 63.717511879
        }, 
        {
            "nm" : "ANN_ENTITY",
            "val" : "breast tumors",
            "count" : 35,
            "uniqueCount" : 35.717511879
        }, 
        {
            "nm" : "ANN_ENTITY",
            "val" : "cancer",
            "count" : 10,
            "uniqueCount" : 10.717511879
        }, 
        {
            "nm" : "ANN_SECTION_TYPE",
            "val" : "disease%abstract",
            "count" : 10,
            "uniqueCount" : 10.717511879
        }, 
        .......
    ],
    "dateUpdated" : ISODate("2017-10-17T10:10:18.225Z"),
    "dateInserted" : ISODate("2017-10-13T13:48:38.680Z")
}

</pre>

[Swagger][4] is used to generate the API HTML documentation

[1]: https://www.w3.org/TR/annotation-model/
[2]: http://spring.io/guides/gs/rest-service/
[3]: https://github.com/EuropePMC/MongoDB-Layer
[4]: https://swagger.io/
[5]: https://europepmc.org/AnnotationsApi


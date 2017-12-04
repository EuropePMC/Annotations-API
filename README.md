# EuropePMC Annotations API
This project contains the source code used to build the [Europe PMC Annotations API RESTful service][5]

Europe PMC Annotations API provides text mining annotations contained in abstracts and open access full text articles, using the [W3C Open Annotation Data Model][1]

The code has been written using JAVA 8. The development tool used is [Maven][6]. The RESTful service  has been built using [Spring Boot][2]. The artifact produced by the project is a war file ready to be deployed on a Tomcat 8 container.

The Europe PMC Annotations API is reading the data from a MongoDB database. The component that it is managing the interaction with the MongoDB database is described by the following dependency in the POM file

<pre>
&lt;dependency&gt;
       &lt;groupId&gt;uk.ac.ebi.literature&lt;/groupId&gt;
	  &lt;artifactId&gt;mongodb_layer&lt;/artifactId&gt;
	  &lt;version&gt;3.2.0&lt;/version&gt;
	  &lt;exclusions&gt;
		  &lt;exclusion&gt; 
		    &lt;groupId&gt;org.slf4j&lt;/groupId&gt;
		    &lt;artifactId&gt;slf4j-log4j12&lt;/artifactId&gt;
		  &lt;/exclusion&gt;
		  &lt;exclusion&gt; 
		    &lt;groupId&gt;log4j&lt;/groupId&gt;
		    &lt;artifactId&gt;log4j&lt;/artifactId&gt;
		  &lt;/exclusion&gt;
	&lt;/exclusions&gt; 
&lt;/dependency&gt;
</pre>

The MongoDB Layer component can be downloaded [here][3]. 
The MongoDB database from where to read the data is specified by the value of the property mongoDBUrl inside the appropriate property file below the folder src/main/resources. It is possible to determine which is the appropriate property file to load inside the class uk.ac.ebi.scilite.controller.AnnotationsAPIWebApplication. This class is the entry point of the entire application as well. 

The value of the property mongoDBUrl will need to reflect one of the values of the enum MONGODB_URL defined in the class uk.ac.ebi.literature.mongodb.dao.ICrudDAO of the MongoDB Layer component. To see how this value will be translated to a specific MongoDB server address, see details of the MongoDB Layer component README file.

The name of the collection inside the MOngoDB database from where to read the data is specified by the value of the property collectionName.

The Europe PMC Annotations API is expecting that the documents inside the MongoDB database collection have the following structure:

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

 The API HTML documentation is generated using [Swagger][4]

[1]: https://www.w3.org/TR/annotation-model/
[2]: http://spring.io/guides/gs/rest-service/
[3]: https://github.com/EuropePMC/MongoDB-Layer
[4]: https://swagger.io/
[5]: https://europepmc.org/AnnotationsApi
[6]: https://maven.apache.org/


# jpa-demo

Playing with spring-data-rest and JPA.

## Mappings

* Graphs
  * Node: composite key for nodes, surrogate id for edges.  bidirectional.
  * NodePk: composite (natural) key for both nodes and edges.  bidirectional.
  * NodeCollection: Node owns outgoing edges.  Edge links only to target node.

## Usage

* `mvn spring-boot:run`
* Browse to:
  * repositories: http://localhost:8080
  * controller: http://localhost:8080/greeting

### Generating Server Docs

1. `mvn package`
1. Point browser to `file:///C:/Users/.../jpa-demo/target/snippets/docs/index.html`
1. [index.adoc](src/main/resources/asciidoc/index.adoc)

## Refs

* [asciidoctor-maven-plugin](https://github.com/asciidoctor/asciidoctor-maven-plugin)
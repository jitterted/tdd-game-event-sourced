# Design Decisions

## Tags for Events

### Options:

   1. Annotate tags with @Tag
   2. Schema defines which are tags
   3. Method that returns tags for this event

Option 1 is annoying, because then we have to have code that examines annotated record components to find which are tags, and then have to query them to get the tags, and then what are the prefixes for those tags? Are they defined as an annotation parameter?

Option 2 would require more code to support schemas and would require me to know what kind of schemas I want to use.

Option 3 has similar problems to #1, in that we need a way to ensure properties that can be tags are converted to strings in a consistent way. By always using Records (or any class wrapper) for properties that are Tags, we can rely on the wrapper to generate the consistent tag string representation.

### Decision

Option 3, with an interface `Tag` that has an `toTag()` method that prefixes the ID (or record content) with the tag-specific prefix. The EventStore, during append, gets all of the RecordComponents for the event (via record.getRecordComponents()), and for each component, checks (via component.getInterfaces()) that `Tag` is one of the implemented interfaces and, if it is, calls `toTag()` and adds it to the set of tags for that event.

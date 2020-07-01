# EnhancedAdapter

EnhancedAdapter removes the pain of writing a RecyclerView adapter. EnhancedAdapter is an adapter without the boilerplate that supports automatically observers a LiveData list of items as well as displaying placeholders.

## Configuration

In your top-level build.gradle make sure you have jitpack listed as a repository:

```kotlin
allprojects {
    repositories {
        maven {
            url "https://jitpack.io"
        }
        ...
    }
}
```

In your app-specific build.gradle:

`implementation 'com.github.lewisjdeane:EnhancedAdapter:1.0.1'`

## Usage

### Basic

The simplest way to get started with an EnhancedAdapter is to provide a LiveData list of elements:

```kotlin
// Create the adapter by passing in a LiveData list of items.
val adapter = EnhancedAdapter(context, liveItems)

// Set the adapter on the RecyclerView.
recyclerView.adapter = adapter
```

### Handling clicks

Sometimes you need to handle clicks to items in your list, with EnhancedAdapter, this is simple:

```kotlin
val onItemClickedListener =
    object : EnhancedAdapter.OnItemClickedListener {
        override fun onClick(item: Item) {
            // Do something when an item is clicked.
        }
    }

// Create the adapter by passing in a LiveData list of items and a click listener.
val adapter = EnhancedAdapter(context, liveItems, onItemClickedListener)

// Set the adapter on the RecyclerView.
recyclerView.adapter = adapter
```

### Setting a placeholder when there are no elements in the list

If you wish to display a placeholder view when there are no elements in the list, this is also trivial with EnhancedAdapter:

```kotlin
// Create the adapter by passing in a LiveData list of items and a placeholder view.
val adapter = EnhancedAdapter(context, liveItems, placeholder)

// Set the adapter on the RecyclerView.
recyclerView.adapter = adapter
```

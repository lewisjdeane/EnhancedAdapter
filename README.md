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

```kotlin
// Override the base EnhancedAdapter class and implement the layoutRes variable and bind method.
class MyAdapter(
    context: Context,
    liveItems: LiveData<YourItem>,
    placeholder: View? = null, // Optional.
    onItemClickListener: OnItemClickListener? = null // Optional.
) : EnhancedAdapter(context, liveItems, placeholder, onItemClickListener) {
    
    // Tell the adapter what view you wish to inflate for elements.
    override val layoutRes = R.layout.your_item_layout
    
    // Setup the view you've inflated with a given item.
    override fun bind(view: View, item: YourItem) {
        val title = view.findViewById(R.id.title_id)
        val message = view.findViewById(R.id.message_id)
        
        title.text = item.title
        message.text = item.message
    }
}

// Define your click listener.
val onItemClickedListener =
    object : EnhancedAdapter.OnItemClickedListener {
        override fun onClick(item: Item) {
            // Do something when an item is clicked.
        }
    }

// Create the adapter by passing in a LiveData list of items, an optional placeholder view and an optional click listener.
val adapter = MyAdapter(context, liveItems, placeholder, onItemClickListener)

// Set the adapter on the RecyclerView.
recyclerView.adapter = adapter
```

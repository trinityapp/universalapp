# Tutorial #

This Library will help you to add a fragment to your existing app and just needs a Frame Layout to implement it.

### What does it consists? ###
* It consists of lists of Menu and N number submenus.   
* It consists of dynamic form which will create forms acording to your need.


### How do I get set up? ###

* Import this Repository in your Sourcetree and add as Submodule and save it as DynamicForms.
* Open the project in which the library is imported and add this line in the settings.gradle: 
```
include ':DynamicForms'
```
* Open build.gradle(module:app), and add the folowing line:
```
implementation project(':DynamicForms')
```
* Create a Frame Layout in xml file:
```
 <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/container"/>
```
* Add this line in the Activity class:
```
new DynamicFormHelper(MainActivity.this, R.id.container, Base_url, emp_id, role_id, did);
```

### Who do I talk to? ###

* Mompi Devi
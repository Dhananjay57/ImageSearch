# ImageSearch Application (Tripoto assignment)
Application Arcitecture:
Arcitecture of the project follows the principles of MVVM(Model-View-Viewodel) concept.
source:https://github.com/MindorksOpenSource/android-mvvm-architecture

The app has following packages:
1. binding: custombinding adapter for loading images.
2. data: It contains all the data accessing and manipulating components.
3. dependencyInjection: Dependency providing classes using Dagger2.
4. firbase: FireBase Messaging service.
5. ui: base,View classes along with their corresponding ViewModel.
6. utils: Utility classes.

Important Classes and things:
.Splash activity has no layout file, instead the ui is populated using theme for faster load.
.Paging-Infinit scroll support with EndlessRecyclerViewScrollListener.
 source:https://gist.github.com/nesquena/d09dc68ff07e845cc622
.Response are cached offline using okhttp Intercepter.
.CacheManager, CacheInterceptor
 source:https://github.com/JakeWharton/DiskLruCache
.GridColumnsPopupWindow.



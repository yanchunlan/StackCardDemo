# StackCardDemo
注意： 请使用AndroidStudio3.0 以上工具运行项目，或者调低gradle Version  与  android plugin version<br>
多上传上去的  .idea，gradle    请按情况配置自己的环境
####  一 .Android 卡片式堆叠布局<br>

    原理： 自定义CustomLayoutManager  与 ItemTouchHelper 相结合做成的 
   ![](./picture/card.gif)
   
####  二 .针对单Item上下滑动做了上下滑动情况的区分<br>

    原理： 自定义LayoutManager  与 PagerSnapHelper 相结合做成的 

1.向下滑动，倒序（上层是最后一个数据，底层是第一个数据） <br>
    
   ![](./picture/slidingupre.gif)
2.向下滑动，正序（上层是第一个数据，底层是最后一个数据） <br>
    
   ![](./picture/slidinguppos.gif)
3.向上滑动，正序（上层是第一个数据，底层是最后一个数据） <br>
    
   ![](./picture/slidingdownpos.gif)

拓展：topgravity 包里放置了左右滑动单Item，正序的demo
    
    


## Thread
### 1. [两个线程交替打印1-200](./src/main/java/club/zhengxiang/coding/thread/Thread1.java)
* 方法一：volatile + flag 忙等待模式，浪费大量CPU资源
* 方法二：synchronized 等待/通知机制，效率高，实现简单直观
  * 优点：
    * 简单：synchronized 自动获取和释放锁
    * 自动锁管理：JVM 保证进入同步块的每个线程最终会释放锁，减少死锁的风险
    * 可重入：同一个线程可多次获得同一个锁，递归调用时很有用
  * 缺点：
    * 灵活性低：synchronized 只能以阻塞方式执行同步
    * 粒度：synchronized 通常锁住的是整个方法或对象，导致不必要的线程阻塞，降低并发效率
    * 无公平性选项：synchronized 不提供公平性选项，不能保证等待时间最长的线程先获得锁，可能导致线程饥饿
* 方法三：一个 semaphore 公平模式，不能完全实现交替打印
  * 因为 semaphore 只能控制同时执行的线程数量，但不能控制具体的执行顺序，如果线程A释放许可时，线程B还没来得及调用 acquire 进入等待队列，那么线程A会再次获得许可
  * 同时 sleep 和 release 的执行顺序，也会影响交替打印的结果
  * sleep 在 release 之前，更容易交替打印
  ```text
  线程A: acquire() -> 获得许可
  线程A: 打印
  线程A: sleep(1) -> 线程A休眠,但仍持有许可
  // 这时许可不可用,所以线程B必须进入等待队列
  线程A: release() -> 许可直接给到等待队列中的线程B
  ```
  * sleep 在 release 之后，很难完全交替打印
  ```text
  线程A: acquire() -> 获得许可
  线程A: 打印
  线程A: release() -> 释放许可
  // 这时线程B调用 acquire() 会直接获取许可,而不是进入等待队列
  // 等线程A休眠结束后,许可可能已经被线程B使用并释放了
  // 这时线程A和线程B又会开始竞争可用的许可
  线程A: sleep(1) -> 线程A休眠1ms
  ```
* 方法三-2：两个 semaphore 实现严格交替打印
  * 优点：
    * 灵活性高：semaphore 更灵活，可以允许多个线程访问共享资源，而 synchronized 只允许一个线程访问同步代码块
    * 控制能力强：semaphore 的控制能力更强，可以实现复杂的同步任务
    * 提供公平性选项：提供了公平、非公平两种模式，可以控制线程的调度顺序
  * 缺点：
    * 复杂性：semaphore 更复杂，增加了出错的可能性
    * 资源管理：semaphore 不会自动释放许可，可能会导致许可泄漏，导致其他线程无法获取许可
    * 调试困难：semaphore 发生死锁和资源竞争问题时，调试一般比 synchronized 困难
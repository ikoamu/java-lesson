package Q16;

/**
 * Java言語 プログラミングレッスン 下
 * p191 問題16-10
 * 次のSingleThreadProgramクラスは、Job(仕事)というクラスのオブジェクトを
 * 10個作ってworkというメソッドを呼び続けるものです。現在は、シングルスレッド
 * のプログラムになっており、複数の仕事をfor文でぐるぐる順番に実行しています。
 * このプログラムをマルチスレッドに書き換えてください。クラスJobはどこかで宣言
 * されているものとします。
 * 
 * List16-14 SingleThreadProgram.java
 * public class SingleThreadProgram {
 *   Job[] jobs;
 *   public SingleThreadProgram(int jobcount) {
 *     jobs = new Job[jobcount];
 *     for(int i = 0; i < jobcount; i++) {
 *       jobs[i] = new job(i);
 *     }
 *   }
 *   public void workAllJobs(){
 *     for(int i = 0; i < jobs.length; i++) {
 *       jobs[i].work();
 *     }
 *   }
 *   public static void main(String[] args) {
 *     SingleThreadProgram self = new SingleThreadProgram(10);
 *     while(true){
 *       self.workAllJobs();
 *     }
 *   }
 * }
 *
 */

public class MultiThreadProgram {
  Job[] jobs;
  
  public MultiThreadProgram(int jobcount){
    jobs = new Job[jobcount];
    
    for(int i = 0; i < jobcount; i++) {
      jobs[i] = new Job(i);
    }
  }
  
  public void workAllJobs() {
    for(Job job : jobs) {
      job.start();
    }
  }
  
  public static void main(String[] args) {
    MultiThreadProgram self = new MultiThreadProgram(10);
    self.workAllJobs();
  }
}
class Job extends Thread {
  int jobnum;
  
  public Job(int jobnum) {
    this.jobnum = jobnum;
  }
  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + "--> job:" + jobnum );
  }
}

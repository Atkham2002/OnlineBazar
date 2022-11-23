//package uz.nt.firstspring.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import uz.nt.firstspring.service.ProductService;
//
//import javax.annotation.PreDestroy;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
//@Configuration
//@EnableAsync
//@EnableScheduling
//public class ScheduleJob {
//
//    private final ProductService productService;
//    private final FileOutputStream fosStart;
//    private final FileOutputStream fosEnd;
//
//    public ScheduleJob(ProductService productService) throws FileNotFoundException {
//        this.productService = productService;
//        fosStart = new FileOutputStream(getClass().getClassLoader().getResource("templates/start.txt").getPath(), true);
//        fosEnd = new FileOutputStream(getClass().getClassLoader().getResource("templates/end.txt").getPath(), true);
//    }
//
//    @PreDestroy
//    public void destroy() throws IOException {
//        fosEnd.close();
//        fosStart.close();
//    }
//
//
//    @Scheduled(fixedDelay = 1000 * 60 * 60 * 6, initialDelay = 10 * 1000)
//    public void print(){
//        productService.exportLessThanLimit();
//    }
//
//    @Scheduled(fixedDelay = 1000 * 3, initialDelay = 15 * 1000)
//    public void delay() throws InterruptedException {
//        System.out.println("Started new: " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()));
//        Thread.sleep(1000 * new Random().nextInt(5));
//    }
//
//    @Async
//    @Scheduled(fixedRate = 100 * 3, initialDelay = 20 * 1000)
//    public void rate() throws InterruptedException, IOException {
//
//        String s = "Started new: " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()) + " " + Thread.currentThread().getName();
//        fosStart.write(s.getBytes());
//        fosStart.write(System.lineSeparator().getBytes());
//        fosStart.flush();
//        Thread.sleep(1000 * new Random().nextInt(5));
//        String ss = "End of method " + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new Date()) + " " + Thread.currentThread().getName();
//        fosEnd.write(ss.getBytes());
//        fosEnd.write(System.lineSeparator().getBytes());
//        fosEnd.flush();
//
//    }
//
//    @Scheduled(cron = "* * * * * *")
//    public void harJuma(){
//        System.out.println("Kelilar bollar");
//    }
//}

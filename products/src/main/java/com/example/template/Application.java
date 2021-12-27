package com.example.template;

import com.example.template.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
public class Application {

    protected static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);

        ProductRepository productRepository = applicationContext.getBean(ProductRepository.class);
        // 초기 상품 셋팅
        String[] products = {"TV", "MASK", "NOTEBOOK", "TABLE", "CLOCK"};
        int i = 1;
        for(String p : products){
            Product product = new Product();

            product.setImageUrl("https://github.githubassets.com/images/modules/profile/profile-joined-github.png");
            product.setName(p);
            product.setPrice(i*10000);
            product.setStock(i*10);
            product.setImageUrl("/goods/img/"+p+".jpg");

            // 상품 디테일 추가 - 양방향 관계
            ProductOption productOption = new ProductOption();
            productOption.setName(p + "_detail");
            productOption.setDesc(p + "_desc");
            productOption.setProduct(product);

            ProductOption productOption1 = new ProductOption();
            productOption1.setName(p + "구매설명");
            productOption1.setDesc(p + "설명입니다");
            productOption1.setProduct(product);

            product.addProductOptions(productOption);
            product.addProductOptions(productOption1);

            i++;
            productRepository.save(product);
        }
    }



}


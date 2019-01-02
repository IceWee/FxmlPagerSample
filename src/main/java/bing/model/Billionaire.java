package bing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Billionaire
 *
 * @author: IceWee
 * @date: 2019.1.2
 */
@Getter
@ToString
@AllArgsConstructor
public class Billionaire {

    private String rank;
    private String name;
    private String worth;
    private int age;
    private String source;
    private String country;

}

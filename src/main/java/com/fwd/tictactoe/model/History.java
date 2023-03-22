package com.fwd.tictactoe.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


/**
 * @author Setiawan Candrafu
 * @date 3/18/2023
 */
@Data
@Entity
public class History {

    @Id
    @GeneratedValue
    private long id;
    private String userName;
    private String status;
    private Date completeDate;

}

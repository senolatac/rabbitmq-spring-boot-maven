package com.sha.rabbitmqspringbootmaven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author sa
 * @date 14.08.2021
 * @time 15:12
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueObject
{
    private String type;

    private LocalDateTime time;
}

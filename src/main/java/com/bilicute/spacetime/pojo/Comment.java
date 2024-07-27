// Comment.java
package com.bilicute.spacetime.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentatorId;
    private String content;
    private String state;
    private Integer articleId;
    private Integer createUser;
    private LocalDateTime createTime;
    private Boolean auditingState;
    private Integer likes;
}

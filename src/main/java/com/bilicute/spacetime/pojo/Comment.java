// Comment.java
package com.bilicute.spacetime.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer articleId;
    private Integer userId;
    private String content;
    private LocalDateTime createTime;
}

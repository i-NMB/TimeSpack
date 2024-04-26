package com.bilicute.spacetime.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Attention {
    private Integer activeUserId; // 主动关注者的用户ID
    private Integer passiveUserId; // 被关注者的用户ID
    private LocalDateTime attentionTime; // 关注时间
}

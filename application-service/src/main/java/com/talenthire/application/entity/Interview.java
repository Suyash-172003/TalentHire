package com.talenthire.application.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "interview"
)
@Getter
@Setter
@NoArgsConstructor
public class Interview {

}

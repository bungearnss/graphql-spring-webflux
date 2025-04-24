package com.learning.graphql_crud_application.models.response;

import com.learning.graphql_crud_application.models.dto.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class DeleteResponseDto {

    private Integer id;
    private Status status;

}

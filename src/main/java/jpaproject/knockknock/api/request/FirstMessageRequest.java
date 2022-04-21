package jpaproject.knockknock.api.request;

import jpaproject.knockknock.domain.Location;
import lombok.Data;


@Data
public class FirstMessageRequest {
    String senderId;
    String message;
    Location targetLocation;

}

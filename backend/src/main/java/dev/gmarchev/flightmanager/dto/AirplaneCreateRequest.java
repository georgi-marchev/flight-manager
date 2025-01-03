package dev.gmarchev.flightmanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirplaneCreateRequest {

	@NotBlank(message = "Сериен номер е задължителен.")
	private String serialNumber;

	@Min(value = 1, message = "Невалиден идентификатор на модел на самолет.")
	private Long airplaneModelId;
}

package academy.devdojo.springboot.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
    @NotEmpty(message = "O nome do Anime não pode ser vazio.")
    @Schema(description = "Este é o nome do Anime.", example = "One Punch Man", required = true)
    private String name;
}
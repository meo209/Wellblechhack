import com.github.meo209.archer.features.module.ClickGui
import com.github.meo209.archer.utils.math.RotationRandomizer
import com.github.meo209.archer.utils.math.Vec2
import java.util.random.RandomGenerator

class ExponentialRR(
    @property:ClickGui
    var yawLambda: Double = 1.0,
    @property:ClickGui
    var pitchLambda: Double = 1.0
) : RotationRandomizer {

    private val randomGenerator: RandomGenerator = RandomGenerator.getDefault()

    override fun randomize(input: Vec2): Vec2 {
        return Vec2(
            input.x + randomGenerator.nextExponential() / yawLambda,
            input.y + randomGenerator.nextExponential() / pitchLambda
        )
    }
}
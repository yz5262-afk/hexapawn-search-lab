# Validation

Checked on October 19, 2026 with JDK 21.0.5.

- Compiled all eight source files and the independent Checks harness from source; no stale class files were used.
- Passed 566 assertions across 135 reachable board/turn states, including 34 negative-score search cases. The tree's best moves were compared with an independent board-recursion oracle at depths 1, 2, 4 and 6.
- Checked opening legal moves, parent-board preservation, AI move selection and invalid-depth rejection.
- Completed a seeded 20-game smoke run: White depth 4 versus Black depth 1, seed 42; 10 White wins and 10 Black wins. This is a smoke run, not a color-balanced AI strength benchmark.

## Limits

Interactive human input was not exercised end to end. Larger boards and very deep searches were not benchmarked. The policy averages continuations rather than modeling an adversarial minimax opponent. All existing course-framework and teammate author comments remain in the public source.

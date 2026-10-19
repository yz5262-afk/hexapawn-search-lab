# Public-copy fixes

- Make `AIPlayer.selectMove` protected to implement its inherited abstract method.
- Preserve floating-point evaluation scores instead of assigning them to `int`.
- Initialize the best score to negative infinity, allowing legal choices when
  every continuation is unfavorable; keep terminal positions move-free.
- Handle depth-zero tree frontiers explicitly and reject invalid search depths.
- Add a deterministic tie-break seed and a concise match/human-play CLI.
- Add an independent recursive evaluation oracle; the averaging policy is
  preserved and is not silently replaced by minimax.
